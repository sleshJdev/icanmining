import {Component} from "@angular/core";

@Component({
  selector: 'app-home-user-responsibilities',
  styles: [`
    section {
      display: flex;
      justify-content: space-between;
      background-image: linear-gradient(99deg, #6bd0f4, #83f1c0);
      padding: 0 15%;
    }

    picture {
      width: 20em;
    }
    
    img {
      width: 100%;
    }
    
    .info {
      width: 20em;
    }
  `],
  template: `
    <section>
      <div class="info">
        <p class="text--more">Что остается делать Вам?</p>
        <p class="text--less">Прочитать правила, установить программу и заниматься своими делами. Иногда наблюдать, за
          счетчиком монитора за вашими деньгами.</p>
        <button type="button" mat-button>Установить майнер</button>
      </div>
      <picture>
        <img src="../../../../assets/images/miner.jpg">
      </picture>
    </section>`
})
export class UserResponsibilitiesComponent {
}
