import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  bitcoinAddress = {address: ''};
  editable = false;

  constructor(public http: HttpClient) {
  }

  ngOnInit() {
    this.http.get<{ address: string }>('/api/wallet')
      .subscribe(response => this.bitcoinAddress = response);
  }
}
