import {useState} from "react";
import {cancelReservation} from "../../utils";
import {Button, message} from "antd";
import DeleteOutlined from "@ant-design/icons/DeleteOutlined"

function ReservationCancelButton(props) {
    const [isLoading, setIsLoading] = useState(false);

    const handleCancel = async () => {
        const {reservationId, onCancel} = props;
        setIsLoading(true);

        try {
            await cancelReservation(reservationId);
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }

        onCancel();
    };

    return (
        <Button loading={isLoading}
                onClick={handleCancel}
                danger={true}
                shape="round"
                type="primary"
                icon={<DeleteOutlined/>}/>
    )
}

export default ReservationCancelButton;