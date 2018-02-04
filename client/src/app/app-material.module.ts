import {NgModule} from '@angular/core';
import {
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatFormFieldModule,
  MatInputModule,
  MatOptionModule,
  MatRadioModule,
  MatSelectModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatPaginatorModule,
  MatIconModule,
  MatSortModule
} from '@angular/material';

@NgModule({
  imports: [
    MatButtonModule,
    MatSelectModule,
    MatOptionModule,
    MatCheckboxModule,
    MatRadioModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatTabsModule,
    MatTableModule,
    MatButtonToggleModule,
    MatToolbarModule,
    MatDialogModule,
    MatPaginatorModule,
    MatIconModule,
    MatSortModule
  ],
  exports: [
    MatButtonModule,
    MatSelectModule,
    MatOptionModule,
    MatCheckboxModule,
    MatRadioModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatTabsModule,
    MatTableModule,
    MatButtonToggleModule,
    MatToolbarModule,
    MatDialogModule,
    MatPaginatorModule,
    MatIconModule,
    MatSortModule
  ]
})
export class AppMaterialModule {
}
