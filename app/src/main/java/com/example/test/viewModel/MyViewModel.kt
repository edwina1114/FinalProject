package com.example.test.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.test.database.Scene
import com.example.test.R
import com.example.test.database.SceneDatabase
import kotlinx.coroutines.launch

//viewmodel need to know the database dao (passed argument here) for accessing data from the database
class MyViewModel(application: Application) : AndroidViewModel(application){
    //get the reference to the database dao
    private val database = SceneDatabase.getInstance(application).sceneDatabaseDao

    //a list of all scenes or matched scenes from the database (LiveData)
    val sceneList = MediatorLiveData<List<Scene>>()

    //get the selected scene (livedata for easy observing)
    val selectedScene = MutableLiveData<Scene>()


    //list of cities in Taiwan
    //the following way is not working: don't know
    // val cityList : Array<String> = application.resources.getStringArray(R.array.city_array)
    val cityList = arrayOf(
        "花蓮縣", "台東縣", "宜蘭縣", "屏東縣", "台東縣", "高雄市",
        "雲林縣", "彰化縣", "臺南市", "嘉義縣", "嘉義市", "臺中市", "臺北市",
        "新北市", "新竹縣", "新竹市", "基隆市", "苗栗縣", "桃園市", "南投縣", "澎湖縣",
        "金門縣", "連江縣"
    )

    init {
        getAllScenes()
    }

    fun getAllScenes() {  //set the livedata source of the sceneList to be all scenes
        sceneList.addSource(database.loadAllScenes()) { scenes ->
            sceneList.postValue(scenes)
        }
    }

    fun searchScene(name: String) { //set the livedata source of the sceneList to be matched scenes
        sceneList.addSource(database.findScenes(name)) { scenes ->
            sceneList.postValue(scenes)
        }
    }

    fun getScene(sceneId: Long) {  //get the scene from the database by given its id
        viewModelScope.launch {
            selectedScene.value = database.loadOneScene(sceneId)
        }
    }

    fun insertScene(newScene: Scene) {  //add a new scene into the database
        viewModelScope.launch {
            database.insertScene(newScene)
        }
    }

    fun deleteScene(oldScene: Scene) {  //delete a scene from the database
        viewModelScope.launch {
            database.deleteScene(oldScene)
        }
    }

    fun initDB() {  //setup the initial database
        viewModelScope.launch {
            database.insertScene(
                Scene(
                    "花蓮",
                    "昭和58",
                    "下午茶",
                    "花蓮縣花蓮市成功街306號",
                    R.drawable.h1,
                    "因老闆日本學藝期間對於昭和時代的咖啡廳情有獨鍾，故以自己出生年份昭和58年（民國72年）命名。從裝潢到點心都充滿日本懷舊氛圍，這間洋菓子喫茶店是老闆到日本學藝後回家鄉創業的夢想實踐，因為特色獨具且餐點美味，成為IG、網美打卡熱點。"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "Chillland",
                    "酒吧",
                    "970花蓮縣花蓮市南京街330號",
                    R.drawable.h2,
                    "這是第一家開在花蓮的精釀啤酒，如果突然嘴饞的話，來這邊就對了！對尚未熟悉的精釀啤酒的人來說(簡單來說就是不愛苦味的話)，推薦點臺虎精釀的長島冰啤"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "六里屯麵食專家",
                    "午＆晚餐",
                    "970花蓮縣花蓮市中美路303巷2號",
                    R.drawable.h3,
                    "花蓮超人氣牛肉麵就在【六里屯麵食專家】，除了牛肉麵之外，還有羊肉麵、羊骨麵，以及各式美味小菜，牛三寶拼盤也相當推薦！舒適有質感的工業風環境，無論是個人用餐、情侶約會或是小家庭用餐都很合適，【六里屯麵食專家】除了吉安的旗艦店之外，在中美路上也有美崙分店，生意很好，假日用餐時間要儘早來喔！"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "火車頭烤肉屋",
                    "午＆晚餐",
                    "970花蓮縣花蓮市中山路151號",
                    R.drawable.h4,
                    "道地美式豬肋排、德州式牛胸肉鮮嫩口感讓人無法忘懷！🤤這家美國主廚以德州烤肉、BBQ豬肋排及牛排聞名！"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "西村的家",
                    "午＆晚餐",
                    "973花蓮縣吉安鄉慶豐十街195號",
                    R.drawable.h5,
                    "肉燥飯加半熟蛋！在日式老宅中品嚐古早味麵食的好滋味🏠起初店裡的肉燥飯就只是單純的肉燥飯，直到有一天因為客人的需求才放上一顆半熟荷包蛋，客人一吃感覺就是在吃周星馳電影《食神》中出現的「黯然銷魂飯」一樣，從此以後，肉燥飯加上半熟蛋的黯然銷魂組合就這麼誕生了。"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "奉珠吧",
                    "酒吧",
                    "973花蓮縣吉安鄉福興六街38號1樓",
                    R.drawable.h6,
                    "鄉間小路旁的台式熱炒FEAT. 調酒奉珠吧是久六庫豬民宿的老闆在民宿後院自己打造的酒吧。"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "阿秀奶奶",
                    "下午茶",
                    "970花蓮縣花蓮市新港街79-4號",
                    R.drawable.h7,
                    "阿秀奶奶是一間文青又可愛的花蓮早午餐咖啡館，👵🏻美食口味在精不在多，而且還在特別，它的清粥小菜、奶油紅豆小圓球，簡單樸實又不失親切的溫度，就像老闆阿秀對奶奶的回憶一樣溫暖。還有豐盛的營養大餐盤，輕食沙拉營養豐富，再來份日式焦糖布丁更是滿足！"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "歪歪歪",
                    "下午茶",
                    "970花蓮縣花蓮市南京街328號",
                    R.drawable.h8,
                    "位於後山花蓮甜點店「歪歪歪甜點」，是下午茶吃點心超高CP值的首選🧁。以販售塔類甜點為主，平均只要$55的甜甜價，使用真材實料有著不輸人的賣相以及美妙的滋味，最特別的是現點現做的甜點，吸引了不少喜歡甜食的人。"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "島東譯電所",
                    "酒吧",
                    "970花蓮縣花蓮市福建街436號",
                    R.drawable.h9,
                    "花蓮市溝仔尾區域，有一棟磚造的二層樓獨立建築，曾是鐵路局長的宿舍，如今打開門走進去，彷彿進入一個隨時誘發好奇心的迷幻世界，簡直是什麼都有、什麼都好奇怪。走進「島東譯電所」，牆上的西醫解剖圖、桌上的中醫穴道人體模型、櫃子裡近百年的手搖音樂盒、鱷魚和貓頭鷹標本，都是令人新奇的玩意，這裡是選物店、藝術空間，也是間提供客家點心及調酒的小酒吧"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "惦惦",
                    "下午茶",
                    "973花蓮縣吉安鄉中興路345號",
                    R.drawable.h10,
                    "這一間「惦惦lab:tiamtiam」在IG上是非常熱門的打卡店，地點就在超夯的「慶修院」旁邊，有悠閒的環境，不限制用餐時間，有插座又有WIFI，真的挺不賴的！"
                )
            )
            database.insertScene(
                Scene(
                    "花蓮",
                    "韓鍋家",
                    "午＆晚餐",
                    "970花蓮縣花蓮市中山路889號",
                    R.drawable.h11,
                    "必吃美食有部隊鍋、韓式炸雞、海鮮煎餅CP值很高的一家花蓮在地韓式料理"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "好初早餐",
                    "早午餐",
                    "220新北市板橋區文化路二段125巷70號",
                    R.drawable.b1,
                    "創立於2011年的好初早餐，大概是在板橋地區，跳脫傳統早餐以平價、有趣、洋味十足的菜單卻又具濃厚台味的起源級超人氣早午餐名店。"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "起點咖啡",
                    "早午餐",
                    "220新北市板橋區文化路一段285巷2弄2號",
                    R.drawable.b2,
                    "起點咖啡BEGIN AGAIN是板橋超人氣的早午餐餐廳，店內主打著早午餐、漢堡、三明治、咖啡、茶飲，餐點種類豐富多樣化，平均價格落在200上下，豐盛平價的早午餐讓起點咖啡成為板橋早午餐推薦首選"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "Merci vielle",
                    "下午茶",
                    "220新北市板橋區府中路50號2樓",
                    R.drawable.b3,
                    "非常隱密的古物收藏老宅咖啡廳，店內販售各式甜點、咖啡飲品，是個可以坐下來悠閒品嘗下午茶的好地方！"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "奈野咖啡",
                    "早午餐",
                    "220新北市板橋區文化路二段182巷3弄38號",
                    R.drawable.b4,
                    "店面小小一間(真的很容易不小心經過但走進去會有一種「麻雀雖小，五臟俱全」的感覺，木質的裝潢讓人感到特別溫暖、放鬆，一樓座位不多，主要座位區都在B1(沒錯，你沒看錯，是B1)！"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "男子漢早午餐\n",
                    "早午餐",
                    "220新北市板橋區仁化街179號",
                    R.drawable.b5,
                    "三位男子漢老闆將提供最新鮮最Man的餐點給各位，讓喜歡吃早餐的你們吃的爽吃的飽！"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "Yuly早午餐吧\n",
                    "早午餐",
                    "220新北市板橋區雙十路三段10巷16號",
                    R.drawable.b6,
                    "Yuly早午餐吧的餐點不僅內容非常豐富，價格上更是親民等級，單點價位都在$100元以內，距離捷運江子翠站也很近，走路1分鐘就會到囉～"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "甜福早午餐",
                    "早午餐",
                    "220新北市板橋區公館街86號",
                    R.drawable.b7,
                    "板橋的一家早午餐店，離板橋車站不遠，走路幾分鐘即可抵達。餐點以早午餐拼盤和漢堡類為主。"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "FlagPasta",
                    "午&晚餐",
                    "220新北市板橋區陽明街23巷5號",
                    R.drawable.b8,
                    "評價中的高水準，三種麵、四種醬料任君挑選!"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "雅米早午餐",
                    "早午餐",
                    "220新北市板橋區中山路二段82號",
                    R.drawable.b9,
                    "位於捷運新埔站1號出口的附近，算是這個區域比較年長的板橋早午餐店家，餐點多元，價格便宜，不定時還會推出新菜色，特殊節慶店員還會換上應景服飾。"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "早喚",
                    "早午餐",
                    "220新北市板橋區民權路202巷8弄6號",
                    R.drawable.b10,
                    "早喚 Morning Call與台北知名蛋餅店軟食力師出同門，以知名的粉漿蛋餅受到歡迎，在去年底在行天宮站更展了第二店。早喚沿續同樣的配方及菜單，除了招牌軟Q的蛋餅，另外地瓜球、地瓜薯條及蘿蔔糕，也表現得很出色，成了板橋早午餐生力軍。"
                )
            )
            database.insertScene(
                Scene(
                    "板橋",
                    "星馬廚房",
                    "午&晚餐",
                    "220新北市板橋區文化路一段311-2號",
                    R.drawable.b11,
                    "供應的餐點包括主打的海南雞飯和燒雞腿飯，以及南洋咖哩飯和撈麵、泰式椒麻雞排和豬排飯，還有多款南洋風格點心以及飲料等。"
                )
            )
        }
    }
}