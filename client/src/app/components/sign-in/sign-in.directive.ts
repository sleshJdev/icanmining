import {Directive, HostListener} from '@angular/core';
import {MatDialog} from "@angular/material";
import {SignInComponent} from "./sign-in.component";

@Directive({
  selector: '[appSignIn]'
})
export class SignInDirective {

  constructor(private dialog: MatDialog) {
  }

  @HostListener('click', ['$event.target']) onClick() {
    this.dialog.closeAll();
    this.dialog.open(
      SignInComponent,
      {backdropClass: 'app-backdrop'});
  }

}
