import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../common/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  userOptions: FormGroup;
  cardOptions: FormGroup;

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.userOptions = this.formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
    this.cardOptions = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      cardNumber: new FormControl('', [Validators.required]),
      expirationDate: new FormControl('', [Validators.required]),
      cvv: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required])
    });
  }
}
