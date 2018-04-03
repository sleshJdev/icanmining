import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';
import {AppMaterialModule} from './app-material.module';
import {AppRoutesModule} from './app-routes.module';
import {SignInComponent} from './components/sign-in/sign-in.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NavComponent} from './components/nav/nav.component';
import {HttpClientModule} from '@angular/common/http';
import {SettingsComponent} from './components/settings/settings.component';
import {httpInterceptorProviders} from './common/htpp-interceptor';
import {AuthService} from './common/auth.service';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {InfoModalComponent} from './common/modal/info-modal/info-modal.component';
import {UsersComponent} from './components/users/users.component';
import {WalletComponent} from './components/wallet/wallet.component';
import {DownloadComponent} from './components/download/download.component';
import {HomeModule} from "./components/home/home.module";
import {SignInDirective} from './components/sign-in/sign-in.directive';
import {SignUpDirective} from './components/sign-up/sign-up.directive';


@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    NavComponent,
    SettingsComponent,
    SignUpComponent,
    InfoModalComponent,
    UsersComponent,
    WalletComponent,
    DownloadComponent,
    SignInDirective,
    SignUpDirective
  ],
  imports: [
    BrowserModule,
    AppMaterialModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppRoutesModule,
    HomeModule
  ],
  entryComponents: [
    InfoModalComponent
  ],
  providers: [
    AuthService,
    httpInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
