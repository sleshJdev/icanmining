import {Component, Input} from "@angular/core";
import {MatDialog} from "@angular/material";
import {SignUpComponent} from "./sign-up.component";
import {Route} from "@angular/router";

@Component({
  selector: 'app-sign-up-button',
  template: `
    <button type="button" mat-button *ngIf="visible" (click)="signUp()">Sign Up</button>`
})
export class SignUpButtonComponent {
  @Input() visible: boolean;

  constructor(private dialog: MatDialog) {
  }

  signUp() {
    this.dialog.open(
      SignUpComponent,
      {backdropClass: 'app-backdrop'}
    );
  }
}
