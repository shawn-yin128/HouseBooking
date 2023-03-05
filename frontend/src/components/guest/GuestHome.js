import {Tabs} from "antd";
import TabPane from "antd/es/tabs/TabPane";
import ReservationManagement from "./ReservationManagement";
import ReservationStaySearch from "./ReservationStaySearch";

function GuestHome() {
    return (
        <Tabs defaultActiveKey="1" destroyInactiveTabPane={true} className="host_guest_home">
            <TabPane tab="Search Stays" key="1">
                <ReservationStaySearch/>
            </TabPane>
            <TabPane tab="My Reservations" key="2">
                <ReservationManagement/>
            </TabPane>
        </Tabs>
    )
}

export default GuestHome;