package com.example.proyecto_tp3_kotlin.model

class DogProvider {
    companion object {
        val dogList = listOf(
            Dog(
                name = "Nano",
                breed = "Salchicha",
                subBreed = "",
                gender = "Macho",
                age = 1,
                photo = "https://google.com.loquesa.jpg"
            ),

            Dog(
                name = "Paisana",
                breed = "Border Collie",
                subBreed = "",
                gender = "Hembra",
                age = 10,
                photo = "https://www.diarioconvos.com/wp-content/uploads/2023/10/record-mundial-de-spider-man-828x548.jpg"
            ),

            Dog(
                name = "Colita",
                breed = "Caniche",
                subBreed = "Toy",
                gender = "Hembra",
                age = 3,
                photo = "https://i0.wp.com/imgs.hipertextual.com/wp-content/uploads/2022/10/daredevil-she-hulk.jpg?fit=2160%2C1448&quality=50&strip=all&ssl=1"


            )
        )
    }
}