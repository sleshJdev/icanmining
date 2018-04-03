import {Component, Input} from "@angular/core";
import {SignInComponent} from "./sign-in.component";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-sign-in-button',
  template: `
    <button type="button" mat-button *ngIf="visible" (click)="signIn()">Sign In</button>`
})
export class SignInButtonComponent {
  @Input() visible: boolean;

  constructor(private dialog: MatDialog) {
  }

  signIn() {
    this.dialog.open(
      SignInComponent,
      {backdropClass: 'app-backdrop'});
  }
}
