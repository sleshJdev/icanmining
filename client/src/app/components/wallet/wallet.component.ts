import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';

export class Wallet {
  address: string;
  balance: number;
  usdAmount: number;
}

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit, OnDestroy {
  pullingSubscription: Subscription;
  wallet: Wallet = new Wallet();

  constructor(public http: HttpClient) {
  }

  private pull() {
    return this.http.get('/api/wallet');
  }

  ngOnDestroy() {
    if (this.pullingSubscription) {
      this.pullingSubscription.unsubscribe();
    }
  }

  ngOnInit() {
    this.pull().subscribe((response: Wallet) => {
      this.wallet = response;
    });
    this.pullingSubscription = Observable
      .interval(30000)
      .switchMap(() => this.pull())
      .subscribe((response: Wallet) => {
        this.wallet = response;
      });
  }
}
