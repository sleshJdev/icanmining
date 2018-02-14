import {Component, OnInit} from '@angular/core';
import {AuthService} from './common/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(protected authService: AuthService) {
  }

  ngOnInit(): void {

  }
}
