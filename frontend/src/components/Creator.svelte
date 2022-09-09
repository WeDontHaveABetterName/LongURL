<script>
    import { create } from "../requester";
    import { isURL } from "../utils";

    let url;
    let algorithm = "SHA-1";
    let length = "500";

    let errorDisplay = false;
    let submitPromise;
    let result = undefined;

    const submit = () => {
        const ok = url && url.length > 0 && isURL(url);
        if (!ok) {
            errorDisplay = true;
            return;
        }
        result = undefined;
        submitPromise = create(url, algorithm, length)
            .then((hash) => {
                result = {
                    success: true,
                    value: hash,
                };
            })
            .catch((error) => {
                result = {
                    success: false,
                    value: error.message,
                };
            });
    };

    const copy = () => {
        const clipboard = window.navigator.clipboard;
        clipboard.writeText(result).then(() => {
            alert("Copied to clipboard.");
        });
    };
</script>

<section class="section">
    <div class="container">
        <div class="content">
            <h1 class="title is-1">URL Lengthener</h1>
            <h2 class="subtitle is-3">Paste your URL and watch it grow from the comfort of your home!</h2>
        </div>
    </div>
    <div class="container mt-5">
        <form class="form">
            <div class="field has-addons">
                <div class="control is-expanded">
                    <input id="input_url" class="input is-large is-rounded" type="text" placeholder="Paste your URL here..." bind:value={url} class:is-danger={errorDisplay}/>
                </div>
                <div class="control">
                    <span class="select is-large">
                        <select bind:value={algorithm}>
                            <option value="MD5">MD5</option>
                            <option value="SHA-1" selected>SHA-1</option>
                            <option value="SHA-256">SHA-256</option>
                        </select>
                    </span>
                </div>
                <div class="control">
                    <span class="select is-large">
                        <select bind:value={length}>
                            <option value="500" selected>Normal</option>
                            <option value="1000">Crazy</option>
                            <option value="2000">Insane</option>
                        </select>
                    </span>
                </div>
                <div class="control">
                    {#await submitPromise}
                        <button class="button is-large is-rounded" disabled on:click|preventDefault>
                            <span>
                                Go
                            </span>
                            <span class="icon">
                                <i class="fas fa-spinner fa-pulse"/>
                            </span>
                        </button>
                    {:then} 
                        <button class="button is-link is-large is-rounded" on:click|preventDefault={submit}>
                            <span>
                                Go
                            </span>
                            <span class="icon">
                                <i class="fas fa-paper-plane"/>
                            </span>
                        </button>
                    {/await}

                </div>
            </div>
        </form>
    </div>
    <div class="container mt-5">
        <div class="container">
            {#if result}
                {#if result.success}
                    <h2 class="response">Your URL has been generated, click the box to copy it to the clipboard.</h2>
                    <pre on:click={copy}>{window.location.href}{result.value}</pre>
                {:else}
                    <h2>An error occurred: {result.value}</h2>
                {/if}
            {/if}
        </div>
    </div>
</section>

<style>
    .title {
        font-weight: 900;
    }

    .subtitle {
        font-weight: 400;
    }

    .response {
        font-weight: 500;
    }

    pre {
        font-weight: 300;
        cursor: pointer;
    }
</style>