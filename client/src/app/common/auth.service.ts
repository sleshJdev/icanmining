import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class AuthService implements OnInit {
  private tokenHeaderNameKey = 'X-Auth-Token';
  private authDetails = null;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.restore();
  }

  get authInfo(): any {
    if (!this.authDetails) {
      this.restore();
    }
    return this.authDetails;
  }

  public restore() {
    const tokenHeaderName = localStorage.getItem(this.tokenHeaderNameKey);
    this.authDetails = JSON.parse(localStorage.getItem(tokenHeaderName));
  }

  get token(): { name: string, value: string } {
    return this.isAuthorized() ? this.authDetails.token : {};
  }

  get user(): { username: string } {
    return this.isAuthorized() ? this.authDetails.user : {};
  }

  isAuthorized() {
    return !!this.authInfo;
  }

  signOut() {
    this.authDetails = null;
    localStorage.removeItem(this.tokenHeaderNameKey);
  }

  singIn(credentials) {
    return this.http.post('/api/sign-in', credentials)
      .map((authDetails: any) => {
        this.authDetails = authDetails;
        const token = authDetails.token;
        localStorage.setItem(this.tokenHeaderNameKey, token.name);
        localStorage.setItem(token.name, JSON.stringify(authDetails));
        return authDetails.user;
      });
  }

  signUp(user) {
    return this.http.post('/api/sign-up', user);
  }

}
