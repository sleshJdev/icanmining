import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {
  protected pullingSubscription: Subscription;
  protected wallet: Wallet = new Wallet();

  constructor(private http: HttpClient) {
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

export class Wallet {
  address: string;
  balance: number;
  usdAmount: number;
}
