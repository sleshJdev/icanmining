import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../common/auth.service';
import {InfoModalComponent} from '../../common/modal/info-modal/info-modal.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  protected options: FormGroup;

  constructor(private authService: AuthService,
              private dialog: MatDialog,
              private router: Router,
              private formBuilder: FormBuilder) {
    this.options = formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  signUp() {
    this.authService.signUp(this.options.value)
      .switchMap(() => {
        return this.dialog.open(InfoModalComponent, {
          width: '300px',
          data: {
            title: 'Registration',
            message: `Congrats, ${this.options.value.username} !
            Your account was created successfully!
            Please, following to the login page and try login
            to download our miner. `
          }
        }).afterClosed();
      }).subscribe(() => {
      this.router.navigateByUrl('/sign-in');
    });
  }
}
