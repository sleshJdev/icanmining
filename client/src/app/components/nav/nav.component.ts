import {Component} from '@angular/core';
import {AuthService} from '../../common/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {

  constructor(private router: Router,
              protected authService: AuthService) {
  }

  signOut() {
    this.authService.signOut();
    this.router.navigateByUrl('/sign-in');
  }

}
