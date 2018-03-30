import {Component} from "@angular/core";

@Component({
  selector: 'app-home-icanmining',
  styles: [`
    .reasons {
      display: flex;
      justify-content: space-around;
      padding: 0 15%;
    }

    .reason {
      width: 20%;
    }
    
    img {
      width: 100%;
    }
  `],
  template: `
    <section>
      <p class="text--more text--center">
        <span>---</span>
        Почему ICAN MINING?
        <span>---</span>
      </p>
      <div class="reasons">
        <div class="reason">
          <picture>
            <img src="../../../../assets/images/miner.jpg">
          </picture>
          <p>
            Не требуется начального капитала.
          </p>
        </div>
        <div class="reason">
          <picture>
            <img src="../../../../assets/images/miner.jpg">
          </picture>
          <p>
            Не нужно обладать особыми знаниями о майнинге криптовплюты.
          </p>
        </div>
        <div class="reason">
          <picture>
            <img src="../../../../assets/images/miner.jpg">
          </picture>
          <p>
            Не нужно тратить личное время, все происходит автоматически.
          </p>
        </div>
        <div class="reason">
          <picture>
            <img src="../../../../assets/images/miner.jpg">
          </picture>
          <p>
            Не нужно заморачиваться доставкой денег на счет.
          </p>
        </div>
      </div>
    </section>`
})
export class IcanminingComponent {
}
