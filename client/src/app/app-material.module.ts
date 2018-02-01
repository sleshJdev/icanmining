import {NgModule} from '@angular/core';
import {
  MatButtonModule, MatButtonToggleModule, MatCardModule, MatCheckboxModule, MatExpansionPanel, MatFormFieldModule, MatInputModule,
  MatOptionModule, MatPlaceholder,
  MatRadioModule,
  MatSelectModule, MatTableModule, MatTabsModule, MatToolbarModule
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
    MatToolbarModule
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
    MatToolbarModule
  ]
})
export class AppMaterialModule {
}
