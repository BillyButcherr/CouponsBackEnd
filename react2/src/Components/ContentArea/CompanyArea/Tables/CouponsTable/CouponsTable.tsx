import { useEffect, useState } from "react";
import CouponItem from "../../../../../Models/Coupon/CouponItem";
import "./CouponsTable.css";
import CouponRow from "./CouponRow/CouponRow";
import CompanyService from "../../../../../Services/CompanyService";
import DeleteDialog from "../../../Dialogs/DeleteDialog/DeleteDialog";
import { CompanyCouponsStore, deleteCoupon } from "../../../../../Store/CompanyCouponsState";
import EditDialog from "../../../Dialogs/EditDialog/EditDialog";
import ImageDialog from "../../../Dialogs/ImageDialog/ImageDialog";
import AddDialog from "../../../Dialogs/AddDialog/AddDialog";

function CouponsTable(props:any): JSX.Element {
    const [tableItems, setTableItems] = useState<CouponItem[]>([]);

    const [deleteModal, setDeleteModal] = useState(false);
    const [editModal, setEditModal] = useState(false);
    const [imageModal, setImageModal] = useState(false);
    const [addModal, setAddModal] = useState(false);


    const [deleteItemId, setDeleteItemId] = useState("");
    const [editItemId, setEditItemId] = useState("");
    const [selectedItemId, setSelectedItemId] = useState("");
    const [selectedCoupon, setSelectedCoupon] = useState<CouponItem>();




    useEffect(() => {
        new CompanyService("2").getAllCoupons().then(coupons => {
            setTableItems(coupons);
        }).catch(err => console.log(err));
    }, []);

    function deleteRow(couponId:string) {
        CompanyCouponsStore.dispatch(deleteCoupon(couponId));
        const updatedCouponItems = tableItems.filter((item: CouponItem) => item.id !== couponId);
        setTableItems(updatedCouponItems);
    }
    function editRow(coupon:CouponItem) {
        new CompanyService("2").updateCoupon(coupon).then(coupon => {
            //Updated
        }).catch(err => console.log(err));
        // CompanyCouponsStore.dispatch(deleteCoupon(couponId));
        // const updatedCouponItems = tableItems.filter((item: CouponItem) => item.id !== couponId);
        // setTableItems(updatedCouponItems);
    }
    function addCoupon(){

    }
    function updateRow(coups:CouponItem[]){
    }
    function changeImage(couponId:string, imageUrl:string){
        new CompanyService("2").getOneCoupon(couponId).then(coupon => {
            coupon.image = imageUrl;
            new CompanyService("2").updateCoupon(coupon).then(coupon => {
                //Updated
            }).catch(err => console.log(err));        
        }).catch(err => console.log(err));
    }
    // alert("rendered");
    return (
        <>
        <table className="CouponsTable">
			  <thead>
                    <tr>
                        <th>מספר</th>
                        <th>כותרת</th>
                        <th>תיאור</th>
                        <th>קטגוריה</th>
                        <th>תאריך התחלה</th>
                        <th>תאריך סיום</th>
                        <th>כמות</th>
                        <th>מחיר</th>
                        <th>פעולות</th>
                    </tr>
                </thead>
                <tbody>
                    {tableItems.map((item: { id: any; }) => (
                        <CouponRow key={item.id} hooks={[setDeleteModal, setEditModal, setImageModal, setDeleteItemId, setSelectedCoupon]} info={item} />
                    ))}
                </tbody>
            </table>
            <button className="add-button" onClick={ () => setAddModal(true)}>הוספה</button>

            <DeleteDialog hooks={[deleteModal, setDeleteModal, deleteRow, deleteItemId]}/>
            <EditDialog hooks={[editModal, setEditModal, editRow, selectedCoupon]}/>
            <ImageDialog hooks={[imageModal, setImageModal, changeImage, selectedCoupon]}/>
            <AddDialog hooks={[addModal, setAddModal, addCoupon, selectedCoupon]}/>


            {/* <DeleteConfirmation hooks={[open, setOpen, currentDelete, setCurrentDelete, deleteRow]}/> */}
            {/* <button className="add-button" onClick={addCompany}>הוספה</button> */}
            {/* <CompanyCreateForm  hooks={[createModal, setCreateModal, companyInfo, setCompanyInfo, updateTable]}/>
            <DeleteConfirmation hooks={[deleteModal, setDeleteModal, deletePayload, setDeletePayload, deleteFromTable]}/>
            <CompanyUpdateForm  hooks={[updateModal, setUpdateModal, companyInfo, setCompanyInfo, updateTable]}/>
            <PasswordReset  hooks={[passwordResetModal, setPasswordResetModal, companyPassword, setCompanyPassword, companyInfo]}/> */}
        </>
    );
}

export default CouponsTable;
