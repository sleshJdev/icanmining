import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {Observable} from "rxjs/Observable";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  protected pullingSubscription: Subscription;
  protected displayedColumns = ['id', 'username', 'profit', 'active'];
  protected dataSource = new MatTableDataSource<UserProfitInfo>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private http: HttpClient) {
  }

  private pull() {
    return this.http.get('/api/profit', {
      params: {
        interval: 'month'
      }
    })
  }

  ngOnDestroy() {
    if (this.pullingSubscription) {
      this.pullingSubscription.unsubscribe();
    }
  }

  ngOnInit() {
    this.pull().subscribe((response: UserProfitInfo[]) => {
      this.dataSource.data = response;
    });
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
