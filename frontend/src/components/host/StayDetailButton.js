import {useState} from "react";
import {Button, Modal, Space, Tooltip} from "antd";
import InfoCircleOutlined from "@ant-design/icons/InfoCircleOutlined"
import Text from "antd/lib/typography/Text";

function StayDetailButton(props) {
    const [visible, setVisible] = useState(false);
    const {stay} = props;
    const {name, description, address, guest_number} = stay;

    const handleOpen = () => {
        setVisible(true);
    };

    const handleCancel = () => {
        setVisible(false);
    };

    return (
        <>
            <Tooltip title="View Stay Detail">
                <Button className="stay_detail_open_button"
                        size="small"
                        icon={<InfoCircleOutlined/>}
                        onClick={handleOpen}/>
            </Tooltip>
            {visible && (
                <Modal title={name}
                       centered={true}
                       open={visible}
                       closable={true}
                       footer={null}
                       onCancel={handleCancel}>
                    <Space direction="vertical">
                        <Text strong={true}>Description</Text>
                        <Text type="secondary">{description}</Text>
                        <Text strong={true}>Address</Text>
                        <Text type="secondary">{address}</Text>
                        <Text strong={true}>Guest Number</Text>
                        <Text type="secondary">{guest_number}</Text>
                    </Space>
                </Modal>
            )}
        </>
    )
}

export default StayDetailButton;