package com.dreyes148.proxyfinder.model

/**
 * Data class representing a country with flag emoji and Spanish name
 */
data class Country(
    val code: String,      // ISO country code (e.g., "US", "MX")
    val nameEs: String,    // Country name in Spanish
    val flag: String       // Flag emoji
) {
    /**
     * Display text showing flag + name
     */
    fun getDisplayName(): String = "$flag $nameEs"
    
    override fun toString(): String = getDisplayName()
}

/**
 * Complete list of world countries with flags and Spanish names
 */
object CountryList {
    val ALL_COUNTRIES = listOf(
        Country("AF", "Afganistán", "🇦🇫"),
        Country("AL", "Albania", "🇦🇱"),
        Country("DE", "Alemania", "🇩🇪"),
        Country("AD", "Andorra", "🇦🇩"),
        Country("AO", "Angola", "🇦🇴"),
        Country("AG", "Antigua y Barbuda", "🇦🇬"),
        Country("SA", "Arabia Saudita", "🇸🇦"),
        Country("DZ", "Argelia", "🇩🇿"),
        Country("AR", "Argentina", "🇦🇷"),
        Country("AM", "Armenia", "🇦🇲"),
        Country("AU", "Australia", "🇦🇺"),
        Country("AT", "Austria", "🇦🇹"),
        Country("AZ", "Azerbaiyán", "🇦🇿"),
        Country("BS", "Bahamas", "🇧🇸"),
        Country("BD", "Bangladés", "🇧🇩"),
        Country("BB", "Barbados", "🇧🇧"),
        Country("BH", "Baréin", "🇧🇭"),
        Country("BE", "Bélgica", "🇧🇪"),
        Country("BZ", "Belice", "🇧🇿"),
        Country("BJ", "Benín", "🇧🇯"),
        Country("BY", "Bielorrusia", "🇧🇾"),
        Country("BO", "Bolivia", "🇧🇴"),
        Country("BA", "Bosnia y Herzegovina", "🇧🇦"),
        Country("BW", "Botsuana", "🇧🇼"),
        Country("BR", "Brasil", "🇧🇷"),
        Country("BN", "Brunéi", "🇧🇳"),
        Country("BG", "Bulgaria", "🇧🇬"),
        Country("BF", "Burkina Faso", "🇧🇫"),
        Country("BI", "Burundi", "🇧🇮"),
        Country("BT", "Bután", "🇧🇹"),
        Country("CV", "Cabo Verde", "🇨🇻"),
        Country("KH", "Camboya", "🇰🇭"),
        Country("CM", "Camerún", "🇨🇲"),
        Country("CA", "Canadá", "🇨🇦"),
        Country("QA", "Catar", "🇶🇦"),
        Country("TD", "Chad", "🇹🇩"),
        Country("CL", "Chile", "🇨🇱"),
        Country("CN", "China", "🇨🇳"),
        Country("CY", "Chipre", "🇨🇾"),
        Country("CO", "Colombia", "🇨🇴"),
        Country("KM", "Comoras", "🇰🇲"),
        Country("CG", "Congo", "🇨🇬"),
        Country("KP", "Corea del Norte", "🇰🇵"),
        Country("KR", "Corea del Sur", "🇰🇷"),
        Country("CI", "Costa de Marfil", "🇨🇮"),
        Country("CR", "Costa Rica", "🇨🇷"),
        Country("HR", "Croacia", "🇭🇷"),
        Country("CU", "Cuba", "🇨🇺"),
        Country("DK", "Dinamarca", "🇩🇰"),
        Country("DM", "Dominica", "🇩🇲"),
        Country("EC", "Ecuador", "🇪🇨"),
        Country("EG", "Egipto", "🇪🇬"),
        Country("SV", "El Salvador", "🇸🇻"),
        Country("AE", "Emiratos Árabes Unidos", "🇦🇪"),
        Country("ER", "Eritrea", "🇪🇷"),
        Country("SK", "Eslovaquia", "🇸🇰"),
        Country("SI", "Eslovenia", "🇸🇮"),
        Country("ES", "España", "🇪🇸"),
        Country("US", "Estados Unidos", "🇺🇸"),
        Country("EE", "Estonia", "🇪🇪"),
        Country("ET", "Etiopía", "🇪🇹"),
        Country("PH", "Filipinas", "🇵🇭"),
        Country("FI", "Finlandia", "🇫🇮"),
        Country("FJ", "Fiyi", "🇫🇯"),
        Country("FR", "Francia", "🇫🇷"),
        Country("GA", "Gabón", "🇬🇦"),
        Country("GM", "Gambia", "🇬🇲"),
        Country("GE", "Georgia", "🇬🇪"),
        Country("GH", "Ghana", "🇬🇭"),
        Country("GR", "Grecia", "🇬🇷"),
        Country("GD", "Granada", "🇬🇩"),
        Country("GT", "Guatemala", "🇬🇹"),
        Country("GN", "Guinea", "🇬🇳"),
        Country("GQ", "Guinea Ecuatorial", "🇬🇶"),
        Country("GW", "Guinea-Bisáu", "🇬🇼"),
        Country("GY", "Guyana", "🇬🇾"),
        Country("HT", "Haití", "🇭🇹"),
        Country("HN", "Honduras", "🇭🇳"),
        Country("HU", "Hungría", "🇭🇺"),
        Country("IN", "India", "🇮🇳"),
        Country("ID", "Indonesia", "🇮🇩"),
        Country("IQ", "Irak", "🇮🇶"),
        Country("IR", "Irán", "🇮🇷"),
        Country("IE", "Irlanda", "🇮🇪"),
        Country("IS", "Islandia", "🇮🇸"),
        Country("IL", "Israel", "🇮🇱"),
        Country("IT", "Italia", "🇮🇹"),
        Country("JM", "Jamaica", "🇯🇲"),
        Country("JP", "Japón", "🇯🇵"),
        Country("JO", "Jordania", "🇯🇴"),
        Country("KZ", "Kazajistán", "🇰🇿"),
        Country("KE", "Kenia", "🇰🇪"),
        Country("KG", "Kirguistán", "🇰🇬"),
        Country("KI", "Kiribati", "🇰🇮"),
        Country("KW", "Kuwait", "🇰🇼"),
        Country("LA", "Laos", "🇱🇦"),
        Country("LS", "Lesoto", "🇱🇸"),
        Country("LV", "Letonia", "🇱🇻"),
        Country("LB", "Líbano", "🇱🇧"),
        Country("LR", "Liberia", "🇱🇷"),
        Country("LY", "Libia", "🇱🇾"),
        Country("LI", "Liechtenstein", "🇱🇮"),
        Country("LT", "Lituania", "🇱🇹"),
        Country("LU", "Luxemburgo", "🇱🇺"),
        Country("MK", "Macedonia del Norte", "🇲🇰"),
        Country("MG", "Madagascar", "🇲🇬"),
        Country("MY", "Malasia", "🇲🇾"),
        Country("MW", "Malaui", "🇲🇼"),
        Country("MV", "Maldivas", "🇲🇻"),
        Country("ML", "Malí", "🇲🇱"),
        Country("MT", "Malta", "🇲🇹"),
        Country("MA", "Marruecos", "🇲🇦"),
        Country("MH", "Islas Marshall", "🇲🇭"),
        Country("MU", "Mauricio", "🇲🇺"),
        Country("MR", "Mauritania", "🇲🇷"),
        Country("MX", "México", "🇲🇽"),
        Country("FM", "Micronesia", "🇫🇲"),
        Country("MD", "Moldavia", "🇲🇩"),
        Country("MC", "Mónaco", "🇲🇨"),
        Country("MN", "Mongolia", "🇲🇳"),
        Country("ME", "Montenegro", "🇲🇪"),
        Country("MZ", "Mozambique", "🇲🇿"),
        Country("MM", "Myanmar", "🇲🇲"),
        Country("NA", "Namibia", "🇳🇦"),
        Country("NR", "Nauru", "🇳🇷"),
        Country("NP", "Nepal", "🇳🇵"),
        Country("NI", "Nicaragua", "🇳🇮"),
        Country("NE", "Níger", "🇳🇪"),
        Country("NG", "Nigeria", "🇳🇬"),
        Country("NO", "Noruega", "🇳🇴"),
        Country("NZ", "Nueva Zelanda", "🇳🇿"),
        Country("OM", "Omán", "🇴🇲"),
        Country("NL", "Países Bajos", "🇳🇱"),
        Country("PK", "Pakistán", "🇵🇰"),
        Country("PW", "Palaos", "🇵🇼"),
        Country("PA", "Panamá", "🇵🇦"),
        Country("PG", "Papúa Nueva Guinea", "🇵🇬"),
        Country("PY", "Paraguay", "🇵🇾"),
        Country("PE", "Perú", "🇵🇪"),
        Country("PL", "Polonia", "🇵🇱"),
        Country("PT", "Portugal", "🇵🇹"),
        Country("GB", "Reino Unido", "🇬🇧"),
        Country("CF", "República Centroafricana", "🇨🇫"),
        Country("CZ", "República Checa", "🇨🇿"),
        Country("CD", "República Democrática del Congo", "🇨🇩"),
        Country("DO", "República Dominicana", "🇩🇴"),
        Country("RW", "Ruanda", "🇷🇼"),
        Country("RO", "Rumania", "🇷🇴"),
        Country("RU", "Rusia", "🇷🇺"),
        Country("WS", "Samoa", "🇼🇸"),
        Country("KN", "San Cristóbal y Nieves", "🇰🇳"),
        Country("SM", "San Marino", "🇸🇲"),
        Country("VC", "San Vicente y las Granadinas", "🇻🇨"),
        Country("LC", "Santa Lucía", "🇱🇨"),
        Country("ST", "Santo Tomé y Príncipe", "🇸🇹"),
        Country("SN", "Senegal", "🇸🇳"),
        Country("RS", "Serbia", "🇷🇸"),
        Country("SC", "Seychelles", "🇸🇨"),
        Country("SL", "Sierra Leona", "🇸🇱"),
        Country("SG", "Singapur", "🇸🇬"),
        Country("SY", "Siria", "🇸🇾"),
        Country("SO", "Somalia", "🇸🇴"),
        Country("LK", "Sri Lanka", "🇱🇰"),
        Country("ZA", "Sudáfrica", "🇿🇦"),
        Country("SD", "Sudán", "🇸🇩"),
        Country("SS", "Sudán del Sur", "🇸🇸"),
        Country("SE", "Suecia", "🇸🇪"),
        Country("CH", "Suiza", "🇨🇭"),
        Country("SR", "Surinam", "🇸🇷"),
        Country("TH", "Tailandia", "🇹🇭"),
        Country("TZ", "Tanzania", "🇹🇿"),
        Country("TJ", "Tayikistán", "🇹🇯"),
        Country("TL", "Timor Oriental", "🇹🇱"),
        Country("TG", "Togo", "🇹🇬"),
        Country("TO", "Tonga", "🇹🇴"),
        Country("TT", "Trinidad y Tobago", "🇹🇹"),
        Country("TN", "Túnez", "🇹🇳"),
        Country("TM", "Turkmenistán", "🇹🇲"),
        Country("TR", "Turquía", "🇹🇷"),
        Country("TV", "Tuvalu", "🇹🇻"),
        Country("UA", "Ucrania", "🇺🇦"),
        Country("UG", "Uganda", "🇺🇬"),
        Country("UY", "Uruguay", "🇺🇾"),
        Country("UZ", "Uzbekistán", "🇺🇿"),
        Country("VU", "Vanuatu", "🇻🇺"),
        Country("VA", "Ciudad del Vaticano", "🇻🇦"),
        Country("VE", "Venezuela", "🇻🇪"),
        Country("VN", "Vietnam", "🇻🇳"),
        Country("YE", "Yemen", "🇾🇪"),
        Country("DJ", "Yibuti", "🇩🇯"),
        Country("ZM", "Zambia", "🇿🇲"),
        Country("ZW", "Zimbabue", "🇿🇼")
    )
    
    /**
     * Find country by name (case-insensitive)
     */
    fun findByName(name: String): Country? {
        return ALL_COUNTRIES.find { it.nameEs.equals(name, ignoreCase = true) }
    }
    
    /**
     * Find country by code (case-insensitive)
     */
    fun findByCode(code: String): Country? {
        return ALL_COUNTRIES.find { it.code.equals(code, ignoreCase = true) }
    }
    
    /**
     * Search countries by query (searches in Spanish name)
     */
    fun search(query: String): List<Country> {
        if (query.isBlank()) return ALL_COUNTRIES
        return ALL_COUNTRIES.filter { 
            it.nameEs.contains(query, ignoreCase = true) 
        }
    }
}
