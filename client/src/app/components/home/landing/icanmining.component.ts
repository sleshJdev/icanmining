import {Component} from "@angular/core";

@Component({
  selector: 'app-home-icanmining',
  styles: [`
    section {
      padding: 0 15%;
    }
    
    .header {
      font-size: 32px;
      line-height: 1.25;
      text-align: center;
      color: var(--color-black);
      margin-bottom: 128px;
    }
    
    .reasons {
      display: flex;
      justify-content: space-between;
    }

    .reason {
      width: 224px;
      font-size: 14px;
      line-height: 1.71;
      text-align: center;
      color: var(--color-black);
    }
    
    .reason__logo  {
      margin: auto;
      width: 160px;
      height: 120px;
    }

    .reason__logo img {
      object-fit: contain;
    }
  `],
  template: `
    <section>
      <p class="header">
        ---Почему ICAN MINING?---
      </p>
      <div class="reasons">
        <div class="reason">
          <div class="reason__logo">
            <img src="../../../../assets/images/money.svg">
          </div>
          <p>
            Не требуется начального капитала.
          </p>
        </div>
        <div class="reason">
          <div class="reason__logo">
            <img src="../../../../assets/images/book.svg">
          </div>
          <p>
            Не нужно обладать особыми знаниями о майнинге криптовплюты.
          </p>
        </div>
        <div class="reason">
          <div class="reason__logo">
            <img src="../../../../assets/images/clock.svg">
          </div>
          <p>
            Не нужно тратить личное время, все происходит автоматически.
          </p>
        </div>
        <div class="reason">
          <div class="reason__logo">
            <img src="../../../../assets/images/card.svg">
          </div>
          <p>
            Не нужно заморачиваться доставкой денег на счет.
          </p>
        </div>
      </div>
    </section>`
})
export class IcanminingComponent {
}
