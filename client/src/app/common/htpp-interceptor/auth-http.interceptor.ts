import {Injectable, Injector} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';

import {AuthService} from '../auth.service';
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthHttpInterceptor implements HttpInterceptor {
  private authService: AuthService;

  constructor(private injector: Injector) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.authService) {
      this.authService = this.injector.get(AuthService);
    }
    req = req.clone({headers: req.headers.set('Content-Type', 'application/json')});
    if (this.authService.isAuthorized()) {
      const token = this.authService.token;
      req = req.clone({headers: req.headers.set(token.name, token.value)});
    }
    return next.handle(req);
  }
}
