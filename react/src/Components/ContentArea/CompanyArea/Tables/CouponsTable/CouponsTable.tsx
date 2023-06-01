import { useEffect, useState } from "react";
import CouponItem from "../../../../../Models/Coupon/CouponItem";
import "./CouponsTable.css";
import CouponRow from "./CouponRow/CouponRow";
import CompanyService from "../../../../../Services/CompanyService";
import { CompanyCouponsStore, addCompanyCoupon, deleteCompanyCoupon, fetchCompanyCoupons, updateCompanyCoupon } from "../../../../../Store/CompanyArea/CompanyCouponsState";
import AddDialog from "../../Dialogs/AddDialog";
import DeleteDialog from "../../Dialogs/DeleteDialog";
import EditDialog from "../../Dialogs/EditDialog";
import ImageDialog from "../../Dialogs/ImageDialog";
import authService from "../../../../../Services/AuthService";
import CouponDTO from "../../../../../Models/Coupon/CouponDTO";
import { Alert, Slide, Snackbar } from "@mui/material";
import notificationService from "../../../../../Services/NotificationService";
function CouponsTable(props: any): JSX.Element {
    const companyId = authService.getAuthData().clientId;
    const [tableItems, setTableItems] = useState<CouponItem[]>([]);

    const [deleteModal, setDeleteModal] = useState(false);
    const [editModal, setEditModal] = useState(false);
    const [imageModal, setImageModal] = useState(false);
    const [addModal, setAddModal] = useState(false);

    const [deleteItemId, setDeleteItemId] = useState("");
    const [selectedCoupon, setSelectedCoupon] = useState<CouponItem>();

    useEffect(() => {
        const unsubscribe = CompanyCouponsStore.subscribe(() => {
            setTableItems([...CompanyCouponsStore.getState().coupons]);
        });
        new CompanyService(companyId).getAllCoupons()
            .then(coupons => setTableItems(coupons))
            .catch(err => console.log(err));
        return unsubscribe;
    }, []);
    function addCouponAccepted(coupon: CouponDTO) {
        coupon.company.id = companyId;
        new CompanyService(companyId).addCoupon(coupon).then(generatedId => {
            notificationService.push(" הקופון נוסף בהצלחה !");
            //Updated
        }).catch(err => console.log(err));
    }
    function editCouponAccepted(coupon: CouponItem) {
        new CompanyService(companyId).updateCoupon(coupon).then(response => {
            notificationService.push(" הקופון עודכן בהצלחה !");
            //Updated
        }).catch(err => console.log(err));
    }
    function changeImageAccepted(couponId: string, imageUrl: string) {
        new CompanyService(companyId).getOneCoupon(couponId).then(coupon => {
            coupon.image = imageUrl;
            new CompanyService(companyId).updateCoupon(coupon).then(response => {
                notificationService.push(" התמונה עודכנה בהצלחה !");
                //Updated
            }).catch(err => console.log(err));
        }).catch(err => console.log(err));
    }
    function deleteCouponAccepted(couponId: string) {
        new CompanyService(companyId).deleteCoupon(couponId).then(response => {
            notificationService.push(" הקופון נמחק בהצלחה !");
            //Updated
        }).catch(err => console.log(err));
    }
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
            <button className="add-button" onClick={() => setAddModal(true)}>הוספה</button>

            <DeleteDialog hooks={[deleteModal, setDeleteModal, deleteCouponAccepted, deleteItemId]} />
            {editModal && <EditDialog hooks={[editModal, setEditModal, editCouponAccepted, selectedCoupon]} />}
            <ImageDialog hooks={[imageModal, setImageModal, changeImageAccepted, selectedCoupon]} />
            <AddDialog hooks={[addModal, setAddModal, addCouponAccepted]} />
        </>
    );
}

export default CouponsTable;
