import {Component} from "@angular/core";

@Component({
  selector: 'app-home-profit',
  styles: [`
    section {
      display: flex;
      padding: 0 15%;
    }

    picture {
      width: 50%;
      margin-right: 2em;
    }

    img {
      width: 100%;
    }
  `],
  template: `
    <section>
      <picture>
        <img src="../../../../assets/images/miner.jpg">
      </picture>
      <div>
        <p class="text--more">Ваша прибыль теперь зависит от мощности видеокарты вашего ПК</p>
        <p class="text--less">Нет скрытых установок, все действия производятся только с вашего разрешения. Все заботы и
          оплаты по конвертации и переводам мы берем на себя. Мы доставим ваши заработанные средства на ваш банковский
          счет в валюте страны проживания. И, как результат, вы получаете 70% от заработанных средств.</p>
        <button type="button" mat-button>
          Начать зарабатывать
        </button>
      </div>
    </section>`
})
export class ProfitComponent {
}
