# Marvel Uygulaması

http://developer.marvel.com/ adresindeki API' yi kullanarak oluşturulan Marvel uygulaması. <br/>
Uygulama Marvel API'sinden alınan "comics", "events", "characters", "creators" ve "series" leri listeler halinde "Home" da göstermektedir.
Kullanıcı "Browse All" a tıklayarak seçtiği kategorinin tümünü listeyebilir. Ayrıntılı incelemek istediği filmi, dergiyi vs. seçerek favori listesini oluşturabilir. "My Library" sekmesinde seçtiği filmleri, dergileri görüntüleyebilir.
"Browse" kısmında Marvel da yer tüm kategorileri görebilir.

## Mimari

Proje mimarisi olarak **MVVM (Model-View-ViewModel)** kullanılmıştır. <br/>

MVVM, kodu modüler bir şekilde düzenlemeye olanak tanır. Model, View ve ViewModel'in ayrı tutulması, kodu daha düzenli ve sürdürülebilir hale getirir. <br/>
**Model:** Veri tabanı, ağ istekleri veya local depolama ile ilgili işlemleri içerir.<br/>
**View:** Aktiviteler, fragmentler aracılığı ile görsel bileşenleri içerir.<br/>
**ViewModel:** Model ve View arasında bir bağlantı sağlar. Kullanıcı arayüzüyle ilgili işlemleri içerir ve View'i doğrudan etkilemeden, Model'den gelen verilere erişim sağlar.<br/>

## Kullanılan Teknolojiler

- Kotlin
- MVVM
- Hilt
- Coroutines
- Retrofit
- LiveData
- Room
- Picasso
- Navigation Components
- ViewPager2
- Frgment

## Ekran Görüntüleri

<img src="screenshots/marvel-app-1.png" alt="görsel 1" width="360" height="720"/> <img src="screenshots/marvel-app-2.png" alt="görsel 2" width="360" height="720"/> <img src="screenshots/marvel-app-3.png" alt="görsel 3" width="360" height="720"/> 
<img src="screenshots/marvel-app-4.png" alt="görsel 4" width="360" height="720"/><img src="screenshots/marvel-app-5.png" alt="görsel 5" width="360" height="720"/>
