import {Component, OnInit} from '@angular/core';
import {MatIconRegistry} from "@angular/material";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private matIconRegistry: MatIconRegistry) {
  }

  ngOnInit(): void {

  }
}
