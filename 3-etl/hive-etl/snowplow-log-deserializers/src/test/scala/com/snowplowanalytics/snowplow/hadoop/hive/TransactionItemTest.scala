/*
 * Copyright (c) 2012 SnowPlow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.snowplow.hadoop.hive

// Specs2
import org.specs2.mutable.Specification

// SnowPlow Utils
import com.snowplowanalytics.util.Tap._

// Deserializer
import test.{SnowPlowDeserializer, SnowPlowEvent, SnowPlowTest}

class TransactionItemTest extends Specification {

	// Toggle if tests are failing and you want to inspect the struct contents
	implicit val _DEBUG = false

	// Transaction item
	val row = "2012-05-25  11:35:53  DFW3  3343  99.116.172.58 GET d3gs014xn8p70.cloudfront.net  /ice.png  200 http://www.psychicbazaar.com/2-tarot-cards/genre/all/type/all?p=5 Mozilla/5.0%20(Windows%20NT%206.1;%20WOW64;%20rv:12.0)%20Gecko/20100101%20Firefox/12.0  &e=ti&ti_id=order-123&ti_sk=PBZ1001&ti_na=Blue%20t-shirt&ti_ca=APPAREL&ti_pr=2000&ti_qu=2&tid=851830&uid=a279872d76480afb&vid=1&aid=CFe23a&lang=en-GB&f_pdf=0&f_qt=1&f_realp=0&f_wma=1&f_dir=0&f_fla=1&f_java=1&f_gears=0&f_ag=0&res=1920x1080&cookie=1&url=file%3A%2F%2F%2Fhome%2Falex%2Fasync.html"
	val expected = new SnowPlowEvent().tap { e =>
		e.dt = "2012-05-25"
		e.tm = "11:35:53"
		e.event = "transaction_item"
		e.txn_id = "851830"
		e.ti_orderid = "order-123"
		e.ti_sku = "PBZ1001"
		e.ti_name = "Blue t-shirt"
		e.ti_category = "APPAREL"
		e.ti_price = "2000"
		e.ti_quantity = "2"
	}

	"The SnowPlow ecommerce transaction item row \"%s\"".format(row) should {

		val actual = SnowPlowDeserializer.deserialize(row)

		// General fields
		"have dt (Date) = %s".format(expected.dt) in {
			actual.dt must_== expected.dt
		}
		"have tm (Time) = %s".format(expected.tm) in {
			actual.tm must_== expected.tm
		}
	    "have event (Event Type) = %s".format(expected.event) in {
	      actual.event must_== expected.event
	    }
	    "have a valid (stringly-typed UUID) event_id" in {
	      SnowPlowTest.stringlyTypedUuid(actual.event_id) must_== actual.event_id
	    }
		"have txn_id (Transaction ID) = %s".format(expected.txn_id) in {
			actual.txn_id must_== expected.txn_id
		}

		// The ecommerce transaction item fields
		"have ti_orderid (Transaction Item Order ID) = %s".format(expected.ti_orderid) in {
			actual.ti_orderid must_== expected.ti_orderid
		}
		"have ti_sku (Transaction Item SKU) = %s".format(expected.ti_sku) in {
			actual.ti_sku must_== expected.ti_sku
		}
		"have ti_name (Transaction Item Name) = %s".format(expected.ti_name) in {
			actual.ti_name must_== expected.ti_name
		}
		"have ti_category (Transaction Item Category) = %s".format(expected.ti_category) in {
			actual.ti_category must_== expected.ti_category
		}
		"have ti_price (Transaction Item Price) = %s".format(expected.ti_price) in {
			actual.ti_price must_== expected.ti_price
		}
		"have ti_quantity (Transaction Item Quantity) = %s".format(expected.ti_quantity) in {
			actual.ti_quantity must_== expected.ti_quantity
		}
	}
}