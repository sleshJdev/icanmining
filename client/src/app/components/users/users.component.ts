import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {Observable} from "rxjs/Observable";
import {Subscription} from "rxjs/Subscription";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  protected pullingSubscription: Subscription;
  protected intervalColor = new FormControl('month');
  protected displayedColumns = ['id', 'username', 'profit', 'active'];
  protected dataSource = new MatTableDataSource<UserProfitInfo>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private http: HttpClient) {
  }

  update() {
    this.pull().subscribe((response: UserProfitInfo[]) => {
      this.dataSource.data = response;
    });
  }

  private pull() {
    return this.http.get('/api/profit', {
      params: {
        interval: this.intervalColor.value
      }
    })
  }

  ngOnDestroy() {
    if (this.pullingSubscription) {
      this.pullingSubscription.unsubscribe();
    }
  }

  ngOnInit() {
    this.pullingSubscription = Observable
      .interval(30000)
      .switchMap(() => this.pull())
      .subscribe((response: UserProfitInfo[]) => {
        this.dataSource.data = response;
      });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}

export interface UserProfitInfo {
  id: number;
  username: string;
  profit: number;
  active: boolean;
}
