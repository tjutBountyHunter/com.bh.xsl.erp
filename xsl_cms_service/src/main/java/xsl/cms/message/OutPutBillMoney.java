package xsl.cms.message;

import com.xsl.cms.mapper.XslOutputBillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import xsl.cms.Utils.DateUtils;
import xsl.cms.commons.JedisClient;
import xsl.cms.pojo.XslOutputBill;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.UUID;

public class OutPutBillMoney implements MessageListener {

    @Value("MONEY_BASE_KEY")
    private String MONEY_BASE_KEY;

    @Resource
    private JedisClient jedisClient;

    @Resource
    private XslOutputBillMapper outputBillMapper;

    private Logger logger = LoggerFactory.getLogger(GetBillMoney.class);

    @Override
    public void onMessage(Message message) {
        //取出消息内容
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            Double outputMoney = new Double(text);
            String money = jedisClient.get(MONEY_BASE_KEY);
            Double baseMoney = new Double(money);

            Double countMoney = baseMoney - outputMoney;

            logger.info("countMoney is {}", countMoney);

            jedisClient.set(MONEY_BASE_KEY, countMoney+"");

            //存入资金流入表
            XslOutputBill outputBill = new XslOutputBill();
            outputBill.setOutputid(UUID.randomUUID().toString());
            outputBill.setOutputMoney(outputMoney);
            outputBill.setTradetime(DateUtils.getDateTimeToString());
            int i;
            try {
                i = outputBillMapper.insert(outputBill);
            }catch (Exception e){
                throw e;
            }
            logger.info("数据条数 i 是 {}"+i);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
