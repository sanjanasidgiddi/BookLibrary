function makeEntry(obj){
    document.write(`<dl class="entry">`)
    for (let key in obj) {
        let value = obj[key]

        document.write("<dt>")
        document.write(key + ":")
        document.write("</dt>")

        document.write("<dd>")
        document.write(value)
        document.write("</dd>")
    }
    document.write(`</dl>`)
}