function makeEntries(objs){
    for (let obj of objs){
        let entry = document.createElement("dl")
        entry.className = "entry"

        entry.innerHTML = ""
        for (let key in obj) {
            let value = obj[key]

            entry.innerHTML += `<dt>${key + ":"}</dt>`
            entry.innerHTML += `<dd>${value}</dd>`
        }
        document.getElementById("entries").appendChild(entry)
    }
}