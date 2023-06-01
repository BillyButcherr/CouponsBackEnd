class ModalState {
    showModal:boolean;
    payload:string;
    accept:Function;
    
    constructor(showModal:boolean, payload:string, accept:Function){
        this.showModal = showModal;
        this.payload = payload;
        this.accept = accept;
    }
    // constructor(showModal:boolean, setShowModal:Function, text:string, acceptText:string, declineText:string, accept:Function, decline:Function, payload:string){
    //     this.showModal = showModal;
    //     this.setShowModal = setShowModal;
    //     this.text = text;
    //     this.acceptText = acceptText;
    //     this.declineText = declineText;
    //     this.accept = accept;
    //     this.decline = decline;
    //     this.payload = payload;
    // }
}
export default ModalState;