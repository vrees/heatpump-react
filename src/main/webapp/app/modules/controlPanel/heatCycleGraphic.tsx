import React, {Component} from 'react';
import {IProcessdata} from "app/shared/model/processdata.model";

interface IGraphicProps {
  sizefactor: number;
  processData: IProcessdata;
}

class HeatCycleGraphic extends Component<IGraphicProps> {
  private canvasRef: React.RefObject<HTMLCanvasElement>;
  private mycontext;
  private mycanvas;

  constructor(props) {
    super(props);
    this.canvasRef = React.createRef();
    this.initpaint = this.initpaint.bind(this);
  }

  componentDidMount(): void {
    const {sizefactor, processData} = this.props;
    this.mycanvas = this.canvasRef.current;
    this.mycontext = this.mycanvas.getContext('2d');

    this.initpaint(this.mycanvas, sizefactor);
  }

  componentDidUpdate(prevProps, prevState) {
    const {sizefactor, processData} = this.props;
    this.initpaint(this.mycanvas, sizefactor);
    this.drawValues(this.mycontext, sizefactor, processData);
  }

  private initpaint(canvas: HTMLCanvasElement, sizefactor: number) {
    canvas.width = 110 * sizefactor;
    canvas.height = 70 * sizefactor;

    this.drawKompressor(this.mycontext, sizefactor);
    this.drawExpansionsventil(this.mycontext, sizefactor);
    this.drawVerdampfer(this.mycontext, sizefactor);
    this.drawVerfluessiger(this.mycontext, sizefactor);
    this.drawKuehlmittelKreis(this.mycontext, sizefactor);
    this.drawLabels(this.mycontext, sizefactor);
    this.drawPfeile(this.mycontext, sizefactor);
    this.drawPumpe(this.mycontext, sizefactor);
  }


  private drawPfeile(ctx, sizefactor) {
    ctx.lineWidth = 2;
    ctx.strokeStyle = "black";

    // Quellwasser Ein
    ctx.beginPath();
    ctx.moveTo(8 * sizefactor, 27 * sizefactor);
    ctx.lineTo(13 * sizefactor, 27 * sizefactor);
    ctx.moveTo(12 * sizefactor, 26.4 * sizefactor);
    ctx.lineTo(13 * sizefactor, 27 * sizefactor);
    ctx.lineTo(12 * sizefactor, 27.6 * sizefactor);
    // Quellwasser Aus
    ctx.moveTo(8 * sizefactor, 43 * sizefactor);
    ctx.lineTo(13 * sizefactor, 43 * sizefactor);
    ctx.moveTo(9 * sizefactor, 42.4 * sizefactor);
    ctx.lineTo(8 * sizefactor, 43 * sizefactor);
    ctx.lineTo(9 * sizefactor, 43.6 * sizefactor);
    // Niederdruck
    ctx.moveTo(38 * sizefactor, 12 * sizefactor);
    ctx.lineTo(43 * sizefactor, 12 * sizefactor);
    ctx.moveTo(42 * sizefactor, 11.4 * sizefactor);
    ctx.lineTo(43 * sizefactor, 12 * sizefactor);
    ctx.lineTo(42 * sizefactor, 12.6 * sizefactor);
    // Hochdruck
    ctx.moveTo(73 * sizefactor, 12 * sizefactor);
    ctx.lineTo(78 * sizefactor, 12 * sizefactor);
    ctx.moveTo(77 * sizefactor, 11.4 * sizefactor);
    ctx.lineTo(78 * sizefactor, 12 * sizefactor);
    ctx.lineTo(77 * sizefactor, 12.6 * sizefactor);
    // Vorlauf
    ctx.moveTo(101 * sizefactor, 27 * sizefactor);
    ctx.lineTo(106 * sizefactor, 27 * sizefactor);
    ctx.moveTo(105 * sizefactor, 26.4 * sizefactor);
    ctx.lineTo(106 * sizefactor, 27 * sizefactor);
    ctx.lineTo(105 * sizefactor, 27.6 * sizefactor);
    // Rücklauf
    ctx.moveTo(101 * sizefactor, 41 * sizefactor);
    ctx.lineTo(106 * sizefactor, 41 * sizefactor);
    ctx.moveTo(102 * sizefactor, 40.4 * sizefactor);
    ctx.lineTo(101 * sizefactor, 41 * sizefactor);
    ctx.lineTo(102 * sizefactor, 41.6 * sizefactor);

    ctx.stroke();
  }


  private drawKuehlmittelKreis(ctx, sizefactor) {
    let rectWidth = 24 * sizefactor;
    const rectHeight = 10 * sizefactor;
    ctx.strokeStyle = "Sienna";
    ctx.lineWidth = 5;
    const cornerRadius = 20;

    // Linie Quelle zu Kompressor
    let rectX = 28 * sizefactor;
    let rectY = 10 * sizefactor;
    ctx.beginPath();
    ctx.moveTo(rectX, rectY + rectHeight);
    ctx.lineTo(rectX, rectY + cornerRadius);
    ctx.arcTo(rectX, rectY, rectX + cornerRadius, rectY, cornerRadius);
    ctx.lineTo(rectX + rectWidth, rectY);
    ctx.stroke();

    // Linie Kompressor Nutzung
    rectX = 64 * sizefactor;
    rectY = 10 * sizefactor;
    ctx.beginPath();
    ctx.moveTo(rectX, rectY);
    ctx.lineTo(rectX + rectWidth - cornerRadius, rectY);
    ctx.arcTo(rectX + rectWidth, rectY, rectX + rectWidth, rectY + cornerRadius, cornerRadius);
    ctx.lineTo(rectX + rectWidth, rectY + rectHeight);
    ctx.lineWidth = 5;
    ctx.stroke();

    // Linie Nutzung Ventil
    rectX = 88 * sizefactor;
    rectY = 50 * sizefactor;
    rectWidth = 26 * sizefactor;
    ctx.beginPath();
    ctx.moveTo(rectX, rectY);
    ctx.lineTo(rectX, rectY + rectHeight - cornerRadius);
    ctx.arcTo(rectX, rectY + rectHeight, rectX - cornerRadius, rectY + rectHeight, cornerRadius);
    ctx.lineTo(rectX - rectWidth, rectY + rectHeight);
    ctx.stroke();

    // Linie Quelle Ventil
    rectX = 28 * sizefactor;
    rectY = 50 * sizefactor;
    ctx.beginPath();
    ctx.moveTo(rectX, rectY);
    ctx.lineTo(rectX, rectY + rectHeight - cornerRadius);
    ctx.arcTo(rectX, rectY + rectHeight, rectX + cornerRadius, rectY + rectHeight, cornerRadius);
    ctx.lineTo(rectX + rectWidth, rectY + rectHeight);
    ctx.stroke();
  }

  private drawVerdampfer(ctx, sizefactor) {
    // Create Verlauf Waermequelle
    const grdquelle = ctx.createLinearGradient(0, 27 * sizefactor, 0, 43 * sizefactor);
    grdquelle.addColorStop(0, "orange");
    grdquelle.addColorStop(1, "DeepSkyBlue");
    ctx.fillStyle = grdquelle;
    ctx.fillRect(22 * sizefactor, 20 * sizefactor, 12 * sizefactor, 30 * sizefactor);
    ctx.rect(22 * sizefactor, 20 * sizefactor, 12 * sizefactor, 30 * sizefactor);
    ctx.lineWidth = 1;
    ctx.strokeStyle = "blue";
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(5 * sizefactor, 25 * sizefactor);
    ctx.lineTo(31 * sizefactor, 25 * sizefactor);
    ctx.arc(31 * sizefactor, 27 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, false);
    ctx.lineTo(25 * sizefactor, 29 * sizefactor);
    ctx.arc(25 * sizefactor, 31 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, true);
    ctx.lineTo(31 * sizefactor, 33 * sizefactor);
    ctx.arc(31 * sizefactor, 35 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, false);
    ctx.lineTo(25 * sizefactor, 37 * sizefactor);
    ctx.arc(25 * sizefactor, 39 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, true);
    ctx.lineTo(31 * sizefactor, 41 * sizefactor);
    ctx.arc(31 * sizefactor, 43 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, false);
    ctx.lineTo(5 * sizefactor, 45 * sizefactor);
    ctx.lineWidth = 1 * sizefactor;
    ctx.strokeStyle = "DarkBlue";
    ctx.stroke();
  }

  private drawVerfluessiger(ctx, sizefactor) {
    // Create Verlauf Verfluessiger
    const grdnutz = ctx.createLinearGradient(0, 27 * sizefactor, 0, 43 * sizefactor);
    grdnutz.addColorStop(0, "red");
    grdnutz.addColorStop(1, "orange");
    ctx.fillStyle = grdnutz;
    ctx.fillRect(82 * sizefactor, 20 * sizefactor, 12 * sizefactor, 30 * sizefactor);
    ctx.rect(82 * sizefactor, 20 * sizefactor, 12 * sizefactor, 30 * sizefactor);
    ctx.lineWidth = 1;
    ctx.strokeStyle = "blue";
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(110 * sizefactor, 25 * sizefactor);
    ctx.lineTo(85 * sizefactor, 25 * sizefactor);
    ctx.arc(85 * sizefactor, 27 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, true);
    ctx.lineTo(91 * sizefactor, 29 * sizefactor);
    ctx.arc(91 * sizefactor, 31 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, false);
    ctx.lineTo(85 * sizefactor, 33 * sizefactor);
    ctx.arc(85 * sizefactor, 35 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, true);
    ctx.lineTo(91 * sizefactor, 37 * sizefactor);
    ctx.arc(91 * sizefactor, 39 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, false);
    ctx.lineTo(85 * sizefactor, 41 * sizefactor);
    ctx.arc(85 * sizefactor, 43 * sizefactor, 2 * sizefactor, 1.5 * Math.PI, 0.5 * Math.PI, true);
    ctx.lineTo(101 * sizefactor, 45 * sizefactor);
    ctx.moveTo(107 * sizefactor, 45 * sizefactor);
    ctx.lineTo(110 * sizefactor, 45 * sizefactor);
    ctx.lineWidth = 1 * sizefactor;
    ctx.strokeStyle = "SeaGreen";
    ctx.stroke();
  }

  private drawKompressor(ctx, sizefactor) {
    // Kompressor
    ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.arc(58 * sizefactor, 10 * sizefactor, 6 * sizefactor, 0.8 * Math.PI, 1.2 * Math.PI, false);
    ctx.lineTo(63.9 * sizefactor, 9 * sizefactor);
    ctx.lineTo(63.9 * sizefactor, 11 * sizefactor);
    ctx.closePath();
    ctx.fillStyle = 'PaleGreen';
    ctx.fill();
    ctx.strokeStyle = "black";
    ctx.stroke();

    // Kompressor Schraffur
    ctx.beginPath();
    ctx.lineWidth = 0.5;
    ctx.moveTo(55 * sizefactor, 6.9 * sizefactor);
    ctx.lineTo(55 * sizefactor, 13.1 * sizefactor);
    ctx.moveTo(57 * sizefactor, 7.3 * sizefactor);
    ctx.lineTo(57 * sizefactor, 12.7 * sizefactor);
    ctx.moveTo(59 * sizefactor, 7.8 * sizefactor);
    ctx.lineTo(59 * sizefactor, 12.2 * sizefactor);
    ctx.moveTo(61 * sizefactor, 8.3 * sizefactor);
    ctx.lineTo(61 * sizefactor, 11.7 * sizefactor);
    ctx.stroke();

    // Kompressor Kreis
    ctx.beginPath();
    ctx.arc(58 * sizefactor, 10 * sizefactor, 6 * sizefactor, 0, 2 * Math.PI, false);
    ctx.lineWidth = 2;
    ctx.strokeStyle = "black";
    ctx.stroke();
  }

  private drawExpansionsventil(ctx, sizefactor) {
    // rechts
    ctx.beginPath();
    ctx.moveTo(58 * sizefactor, 60 * sizefactor);
    ctx.lineTo(62 * sizefactor, 57 * sizefactor);
    ctx.lineTo(62 * sizefactor, 63 * sizefactor);
    ctx.closePath();
    ctx.fillStyle = "PaleGreen";
    ctx.fill();
    ctx.lineWidth = 1;
    ctx.strokeStyle = "blue";
    ctx.stroke();

    // links
    ctx.beginPath();
    ctx.moveTo(58 * sizefactor, 60 * sizefactor);
    ctx.lineTo(54 * sizefactor, 57 * sizefactor);
    ctx.lineTo(54 * sizefactor, 63 * sizefactor);
    ctx.closePath();
    ctx.fillStyle = "PaleGreen";
    ctx.fill();
    ctx.stroke();
  }

  private drawPumpe(ctx, sizefactor) {
    ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.arc(104 * sizefactor, 45 * sizefactor, 3 * sizefactor, 0, 2 * Math.PI, false);

    ctx.moveTo(107 * sizefactor, 45 * sizefactor);
    ctx.lineTo(104 * sizefactor, 42 * sizefactor);
    ctx.moveTo(107 * sizefactor, 45 * sizefactor);
    ctx.lineTo(104 * sizefactor, 48 * sizefactor);
    ctx.strokeStyle = "black";
    ctx.lineWidth = 2;
    ctx.stroke();
  }

  private drawLabels(ctx, sizefactor) {
    // Sizefactor ausgeben
    ctx.font = '8pt Vardena';
    ctx.fillStyle = "black";
    ctx.fillText(sizefactor, 0.5 * sizefactor, 69 * sizefactor);

    // Beschriftungen
    ctx.font = '12pt Verdana';
    ctx.textAlign = 'center';
    ctx.fillText('Kompressor', 58 * sizefactor, 2.8 * sizefactor);
    ctx.fillText('Niederdruck', 40.5 * sizefactor, 15 * sizefactor);
    ctx.fillText('Hochdruck', 75.5 * sizefactor, 15 * sizefactor);
    ctx.fillText('Expansionsventil', 58 * sizefactor, 65.8 * sizefactor);
    ctx.fillText('Pumpe', 104 * sizefactor, 39 * sizefactor);
    ctx.textAlign = 'left';
    ctx.fillText('Verdampfer', 30 * sizefactor, 53 * sizefactor);
    ctx.textAlign = 'right';
    ctx.fillText("Verfluessiger", 86 * sizefactor, 53 * sizefactor);
    ctx.fillText('Boiler', 105 * sizefactor, 62 * sizefactor);

  }

  private drawValues(ctx, sizefactor, pd: IProcessdata) {
    const UNIT_GRAD_CELSIUS = ' °C';
    const UNIT_BAR = ' bar';
    const UNIT_MILLIBAR = ' mbar';
    const EIN = 'Ein';
    const AUS = 'Aus';

    // Sizefactor ausgeben
    ctx.font = '11pt Arial';
    ctx.fillStyle = "blue";

    ctx.fillText(pd.state, 64 * sizefactor, 30 * sizefactor);

    // Temperatures
    ctx.fillText(pd.temperatureEvaporatingIn + UNIT_GRAD_CELSIUS, 14 * sizefactor, 23 * sizefactor);
    ctx.fillText(pd.temperatureEvaporatingOut + UNIT_GRAD_CELSIUS, 14 * sizefactor, 49 * sizefactor);
    ctx.fillText(pd.temperatureFlow + UNIT_GRAD_CELSIUS, 100 * sizefactor, 23.5 * sizefactor);
    ctx.fillText(pd.temperatureReturn + UNIT_GRAD_CELSIUS, 100 * sizefactor, 47.5 * sizefactor);
    ctx.fillText(pd.temperatureOverheatedGas + UNIT_GRAD_CELSIUS, 44 * sizefactor, 8 * sizefactor);
    ctx.fillText(pd.temperatureSwitchOnSensor + UNIT_GRAD_CELSIUS, 105 * sizefactor, 64
      * sizefactor);

    ctx.fillStyle = "brown";
    ctx.fillText(pd.pressureLow + UNIT_BAR, 43 * sizefactor, 18 * sizefactor);
    ctx.fillText(pd.pressureHigh + UNIT_BAR, 78 * sizefactor, 18 * sizefactor);
    ctx.fillText(pd.pressureDiffenceEvaporator + UNIT_MILLIBAR, 20 * sizefactor, 35 * sizefactor);


    ctx.font = '16pt Arial';
    ctx.fillStyle = "red";
    ctx.fillText(pd.operatingStateCompressor === true ? EIN : AUS, 59.5 * sizefactor, 18.2 * sizefactor);
    ctx.fillText(pd.operatingStateWaterPump === true ? EIN : AUS, 105.5 * sizefactor, 50.5 * sizefactor);

    ctx.icon
  }

  render() {
    return (
      <div>
        <canvas ref={this.canvasRef}/>
      </div>
    );
  }
}

export default HeatCycleGraphic;


