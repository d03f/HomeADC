const apiKeys = []

async function reloadApikeyMenu(){
  localStorage.removeItem("apiKey")
  await loadApikeyMenu()
}

async function loadApikeyMenu() {

    let response = await fetch('/libraries/templates/apikey_menu.html');
    let html = await response.text();
    let container = document.getElementById('apikey-menu-placeholder');
    container.innerHTML = html;
  

    let scripts = container.querySelectorAll("script");
    scripts.forEach(script => {
      let newScript = document.createElement("script");
      newScript.text = script.text;
      document.body.appendChild(newScript);
    });
}

document.addEventListener('DOMContentLoaded', loadApikeyMenu);