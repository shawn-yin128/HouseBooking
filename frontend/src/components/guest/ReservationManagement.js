import {useEffect, useState} from "react";
import {getReservations} from "../../utils";
import {Card, List, message} from "antd";
import Text from "antd/lib/typography/Text";
import ReservationCancelButton from "./ReservationCancelButton";

function ReservationManagement() {
    const [isLoading, setIsLoading] = useState(false);
    const [data, setData] = useState([]);

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        setIsLoading(true);

        try {
            const resp = await getReservations();
            console.log(resp);
            setData(resp);
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <List className="reservation_management_list"
              loading={isLoading}
              dataSource={data}
              renderItem={(item) =>
                  <List.Item>
                      <Card title={<Text>{item.stay.name}</Text>}
                            className="reservation_card"
                            extra={<ReservationCancelButton reservationId={item.id} onCancel={loadData}/>}>
                          <Text>Checkin Date: {item.checkin_date}</Text>
                          <br/>
                          <Text>Checkout Date: {item.checkout_date}</Text>
                      </Card>
                  </List.Item>
              }/>
    )
}

export default ReservationManagement;