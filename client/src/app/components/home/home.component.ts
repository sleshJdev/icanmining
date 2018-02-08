import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../common/auth.service';
import {HttpClient} from "@angular/common/http";
import {UserProfitInfo} from "../../model/user-profit-info.";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  protected userProfitInfo = {};

  constructor(private authService: AuthService,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.http.get(`/api/profit/user`)
      .subscribe((response: UserProfitInfo) => {
        this.userProfitInfo = response;
      });
  }
}
