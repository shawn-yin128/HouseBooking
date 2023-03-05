import {useState} from "react";
import {deleteStay} from "../../utils";
import {Button, message} from "antd";
import DeleteOutlined from "@ant-design/icons/DeleteOutlined"

function StayRemoveButton(props) {
    const [isLoading, setIsLoading] = useState(false);

    const handleRemove = async () => {
        const {stay, onRemove} = props;
        setIsLoading(true);

        try {
            await deleteStay(stay.id);
            onRemove();
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Button loading={isLoading}
                onClick={handleRemove}
                danger={true}
                shape="round"
                type="primary"
                icon={<DeleteOutlined/>}/>
    )
}

export default StayRemoveButton;