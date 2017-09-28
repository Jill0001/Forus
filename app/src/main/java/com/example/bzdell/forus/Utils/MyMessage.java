package com.example.bzdell.forus.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * 此类描述的是:首页Listview接收实体
 
 */
public class MyMessage {
		public ArrayList<MyData> data;
		public boolean result;//返回结果
		public int seq;
		public int type;
		

		@Override
		public String toString() {
			return "MyMessage [data=" + data + ",type="+type+" result=" + result + ", seq=" + seq + "]";
		}

		public MyMessage() {
			super();
			data=new ArrayList<MyData>();
		}

		public MyMessage(ArrayList<MyData> data, boolean result, int seq) {
			super();
			this.data = data;
			this.result = result;
			this.seq = seq;
		}

		public ArrayList<MyData> getData() {
			return data;
		}
		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
		public void setData(ArrayList<MyData> data) {
			this.data = data;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		public class MyData implements Serializable {
			 public String content;// 内容
			 public int messageid;// 消息编号
			 public int messagetype;// 消息类型 1 触发点消息 2 定向用户消息
			 public boolean readed;//是否已读
			 public String messagetitle;//消息类型
			 public String messagedate;//消息类型
			 public int Icon_id;

			public int getIcon_id() {
				return Icon_id;
			}

			public void setIcon_id(int icon_id) {
				Icon_id = icon_id;
			}

			public String getMessagetitle() {
				return messagetitle;
			}

			public void setMessagetitle(String messagetitle) {
				this.messagetitle = messagetitle;
			}

			public String getMessagedate() {
				return messagedate;
			}

			public void setMessagedate(String messagedate) {
				this.messagedate = messagedate;
			}

			

			public boolean isReaded() {
				return readed;
			}

			public void setReaded(boolean readed) {
				this.readed = readed;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public int getMessageid() {
				return messageid;
			}

			public void setMessageid(int messageid) {
				this.messageid = messageid;
			}

			public int getMessagetype() {
				return messagetype;
			}

			public void setMessagetype(int messagetype) {
				this.messagetype = messagetype;
			}

			public MyData() {
				super();
			}

			

			public MyData(String content, int messageid, int messagetype, String messagedate) {
				super();
				this.content = content;
				this.messageid = messageid;
				this.messagetype = messagetype;
				this.messagedate = messagedate;
			}

			@Override
			public String toString() {
				return "MyData [content=" + content + ", messageid=" + messageid + ", messagetype=" + messagetype + ",messagetitle"+messagetitle+",messagedate="+messagedate+"]";
			}
			 
		}

	

}
