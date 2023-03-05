import {Tabs} from "antd";
import StayManagement from "./StayManagement";
import StayUpload from "./StayUpload";

const {TabPane} = Tabs;

function HostHome() {
    return (
        <Tabs defaultActiveKey="1" destroyInactiveTabPane={true} className="host_guest_home">
            <TabPane tab="My Stays" key="1">
                <StayManagement/>
            </TabPane>
            <TabPane tab="Upload Stay" key="2">
                <StayUpload/>
            </TabPane>
        </Tabs>
    );
}

export default HostHome;