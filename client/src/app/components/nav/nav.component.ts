import {Component} from '@angular/core';
import {AuthService} from '../../common/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {

  constructor(public router: Router,
              public authService: AuthService) {
  }

  signOut() {
    this.authService.signOut();
    this.router.navigateByUrl('/sign-in');
  }

}
