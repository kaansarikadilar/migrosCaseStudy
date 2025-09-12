Rules --


Proje Yapısı
• Mikro service mimarisine uygun proje yapısı oluşturunuz.
• Spring Boot framework kullanarak geliştiriniz.
• Dev, Test ve Production ortamları için farklı konfigürasyon kullanılarak deploy
edilebilmeli.
• Tasarım prensiplerini uygulamaya çalışınız.
• Commit mesajlarının düzenli ve anlaşılır olmasına özen gösteriniz.
• Geliştirdiğiniz kodun testlerini yazınız. (Unit test, Integration test)
• Uygulamanızı Dockerda çalışabilir hale getiriniz. (Opsiyonel)
Product, Category, Barcode adında 3 mikro service geliştirmeniz bekleniyor. Category servisi
mock data dönen bir servis olabilir.
Product servis içerisinde ürünle ilgili CRUD işlemleri yapılmalı.
Barcode servisinde oluşturulan ürün için barkod üretilmeli ve ürüne bağlanmalıdır.
Ürün ve barkod modeli aşağıdaki gibidir. Farklı özellikler eklenebilir.
Ürün Model Yapısı
• Ad
• Kod
• Kategori
• Ürün barkodları
• Marka
• Birim
Barkod Model Yapısı
• Kod
• Tip
Kurallar
• Ürünün ad ve kod özellikleri tekil olmalıdır.
• Kategori kodu 2 karakter uzunluğunda olmalıdır.
• Ürün kodu 5 karakter olmalıdır ve ilk 2 karakter kategori kodundan gelmelidir.
• Her ürünün bir birimi olmalıdır.
• Her ürünün bir kategorisi olmalıdır.
• Her ürünün bir markası olmalıdır.
• Bir ürünün birden fazla barkodu olabilir. (Aşağıdaki kurallar çerçevesinde) Farklı barkod
tiplerine bağlanabilir.
• Bir barkod sadece bir ürüne bağlanabilir.
• Eğer ürün kategorisi Meyve ve birim Kilogram ise Ürün barkodu ve Kasa barkodu
oluşturulabilir.
• Eğer ürün kategorisi Balık ve birim Kilogram ise Ürün Barkodu ve Terazi Barkodu
oluşturulabilir.
• Eğer ürün kategorisi Balık ve birim Adet ise Kasa barkodu oluşturulabilir.
• Eğer ürün kategorisi Et ise Terazi barkodu girilebilir.
• Diğer kategoriler için sadece ürün barkodu girilebilir.
• Ürün barkodu uzunluğu 9 karakter olmalıdır.
• Terazi barkodu uzunluğu 8 karakter olmalı ve ilk 5 karakter ürün kodundan gelmeli son 3
karakter sequenceden alınmalıdır.
• Kasa barkodu 4 karakter olmalıdır.
