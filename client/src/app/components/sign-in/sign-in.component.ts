import {Component} from '@angular/core';

import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../common/auth.service';
import {MatDialog} from '@angular/material';
import {InfoModalComponent} from '../../common/modal/info-modal/info-modal.component';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {

  options: FormGroup;

  constructor(public authService: AuthService,
              public router: Router,
              public dialog: MatDialog,
              public formBuilder: FormBuilder) {
    this.options = formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  signIn() {
    this.authService.signIn(this.options.value)
      .subscribe((userDetails) => {
        this.router.navigateByUrl('/');
      }, error => {
        return this.dialog.open(InfoModalComponent, {
          width: '300px',
          data: {
            title: 'Bad credentials',
            message: `Username or password are incorrect`
          }
        }).afterClosed();
      });
  }
}
