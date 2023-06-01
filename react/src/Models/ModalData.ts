class ModalData {
    showModal:boolean;
    setShowModal:Function;
    text:string;
    acceptText:string;
    declineText:string;
    accept:Function;
    decline:Function;
    payload:string;
    
    constructor(showModal:boolean, setShowModal:Function, accept:Function, decline:Function, payload:string){
        this.showModal = showModal;
        this.setShowModal = setShowModal;
        this.text = "האם אתה בטוח שברצונך למחוק?";
        this.acceptText = "כן";
        this.declineText = "לא";
        this.accept = accept;
        this.decline = decline;
        this.payload = payload;
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
export default ModalData;