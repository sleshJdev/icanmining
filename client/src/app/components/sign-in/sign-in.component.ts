import {Component, OnInit} from '@angular/core';

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
export class SignInComponent implements OnInit {

  options: FormGroup;

  constructor(private authService: AuthService,
              private router: Router,
              private dialog: MatDialog,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.options = this.formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      rememberme: new FormControl(true)
    });
  }

  signIn() {
    //TODO: implement rememberme
    this.authService.signIn({
      username: this.options.value.username,
      password: this.options.value.password
    }).subscribe(
      () => this.router.navigateByUrl('/'),
      () => this.showError());
  }

  private showError() {
    return this.dialog.open(InfoModalComponent, {
      width: '300px',
      data: {
        title: 'Bad credentials',
        message: `Username or password are incorrect`
      }
    }).afterClosed();
  }
}
