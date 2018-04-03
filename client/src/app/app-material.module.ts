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
  MatSortModule, MatStepperModule
} from '@angular/material';


const modules = [
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
  MatSortModule,
  MatStepperModule
];

@NgModule({
  imports: modules,
  exports: modules
})
export class AppMaterialModule {
}
