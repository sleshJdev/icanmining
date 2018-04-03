import {Component} from "@angular/core";

@Component({
  selector: 'app-home-intro',
  styles: [`
    section {
      margin: auto;
      padding: 0 15%;
      color: var(--color-black);
      text-align: center;
    }

    .motto {
      font-size: 3em;
    }

    .intro {
      font-size: 1.5em;
    }
  `],
  template: `
    <section>
      <p class="motto">
        Быстро, Безопастно, Удобно!
      </p>
      <p class="intro">
        <em>
          Люди , которые не успели вложить в биткоин , часто называют это пузырем , те которые уже вложили , помалкивают
          ,
          вы в этот момент зарабатываете !
        </em>
      </p>
      <button type="button" mat-button>
        Начать Зарабатывать
      </button>
    </section>`
})
export class IntoComponent {

}
