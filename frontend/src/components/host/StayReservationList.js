import {useEffect, useState} from "react";
import {List, message} from "antd";
import {getReservationsByStay} from "../../utils";
import Text from "antd/lib/typography/Text";

function StayReservationList(props) {
    const [isLoading, setIsLoading] = useState(false);
    const [data, setData] = useState([]);

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        setIsLoading(true);
        try {
            const resp = await getReservationsByStay(props.stayId);
            setData(resp);
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <List loading={isLoading}
              dataSource={data}
              renderItem={(item) =>
                  <List.Item>
                      <List.Item.Meta title={<Text>Guest Name: {item.guest.username}</Text>}
                                      description={
                                          <>
                                              <Text>Checkin Date: {item.checkin_date}</Text>
                                              <br/>
                                              <Text>Checkout Date: {item.checkout_date}</Text>
                                          </>
                                      }
                      />
                  </List.Item>
              }/>
    )
}

export default StayReservationList;