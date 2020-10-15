<html>
<head>
<link rel="stylesheet" type="text/css" href="styles/style.css" />
</head>
<body>
<?php

$db = new PDO('mysql:host=127.0.0.1:3306;dbname=pasmanteria;charset=utf8',  
    'pasmanteria', 'pasmanteria1', array(  
        PDO::ATTR_EMULATE_PREPARES => false,  
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION  
    )  
); 

// sprawdza czy wszystkie potrzebne ciastka istnieja
if (isset($_COOKIE['invoice_nr']) && 
	isset($_COOKIE['catalog_number']) &&
	isset($_COOKIE['article_name']) && 
	isset($_COOKIE['discount_percent']) &&
	isset($_COOKIE['tax_percent']) && 
	isset($_COOKIE['price_gross_totality'])) {
	
	//przypisuje wartosci ciastek do zmiennych tablicowych
	$json_str_inv = json_decode($_COOKIE['invoice_nr']);
	$json_str_cat = json_decode($_COOKIE['catalog_number']);
	$json_str_art = json_decode($_COOKIE['article_name']);
	$json_str_dis = json_decode($_COOKIE['discount_percent']);
	$json_str_tax = json_decode($_COOKIE['tax_percent']);
	$json_str_pri = json_decode($_COOKIE['price_gross_totality']);

//czysci zawartosc tabeli bazy danych
$stmt = $db->prepare(  
    "DELETE FROM coordinates_invoice"  
); 	
$stmt->execute();
	
//umieszcza wartosci tablic cookies do tabeli bazy danych
$stmt = $db->prepare(  
    "INSERT INTO coordinates_invoice (field_name, leftx, topy, heighty, widthx) VALUES (:field_name, :leftx, :topy, :heighty, :widthx)"  
);  
$stmt->execute(array(':field_name' => $json_str_inv[0], ':leftx' => $json_str_inv[1], ':topy' => $json_str_inv[2], ':heighty' => $json_str_inv[3], ':widthx' => $json_str_inv[4])); 

$stmt = $db->prepare(  
    "INSERT INTO coordinates_invoice (field_name, leftx, topy, heighty, widthx) VALUES (:field_name, :leftx, :topy, :heighty, :widthx)"  
);  
$stmt->execute(array(':field_name' => $json_str_cat[0], ':leftx' => $json_str_cat[1], ':topy' => $json_str_cat[2], ':heighty' => $json_str_cat[3], ':widthx' => $json_str_cat[4]));

$stmt = $db->prepare(  
    "INSERT INTO coordinates_invoice (field_name, leftx, topy, heighty, widthx) VALUES (:field_name, :leftx, :topy, :heighty, :widthx)"  
);  
$stmt->execute(array(':field_name' => $json_str_art[0], ':leftx' => $json_str_art[1], ':topy' => $json_str_art[2], ':heighty' => $json_str_art[3], ':widthx' => $json_str_art[4]));

$stmt = $db->prepare(  
    "INSERT INTO coordinates_invoice (field_name, leftx, topy, heighty, widthx) VALUES (:field_name, :leftx, :topy, :heighty, :widthx)"  
);  
$stmt->execute(array(':field_name' => $json_str_dis[0], ':leftx' => $json_str_dis[1], ':topy' => $json_str_dis[2], ':heighty' => $json_str_dis[3], ':widthx' => $json_str_dis[4]));

$stmt = $db->prepare(  
    "INSERT INTO coordinates_invoice (field_name, leftx, topy, heighty, widthx) VALUES (:field_name, :leftx, :topy, :heighty, :widthx)"  
);  
$stmt->execute(array(':field_name' => $json_str_tax[0], ':leftx' => $json_str_tax[1], ':topy' => $json_str_tax[2], ':heighty' => $json_str_tax[3], ':widthx' => $json_str_tax[4]));

$stmt = $db->prepare(  
    "INSERT INTO coordinates_invoice (field_name, leftx, topy, heighty, widthx) VALUES (:field_name, :leftx, :topy, :heighty, :widthx)"  
);  
$stmt->execute(array(':field_name' => $json_str_pri[0], ':leftx' => $json_str_pri[1], ':topy' => $json_str_pri[2], ':heighty' => $json_str_pri[3], ':widthx' => $json_str_pri[4]));

echo 'Dane zostały wysłane do aplikacji.<br>';
echo '<script type="text/javascript">window.close();</script>'; //zamyka okno
} 
 ?>
 </body>
 </html>
 
 