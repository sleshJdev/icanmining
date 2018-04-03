import {Directive, HostListener} from '@angular/core';
import {MatDialog} from "@angular/material";
import {SignUpComponent} from "./sign-up.component";

@Directive({
  selector: '[appSignUp]'
})
export class SignUpDirective {

  constructor(private dialog: MatDialog) {
  }

  @HostListener('click', ['$event.target']) onClick() {
    this.dialog.closeAll();
    this.dialog.open(
      SignUpComponent,
      {backdropClass: 'app-backdrop'}
    );
  }

}
