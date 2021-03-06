/** @param {string} [text] */
export function renderLinebreaksHTML(text) {
    return text?.replace(/(\n)|(\r\n)/g, '<br/>');
}

/** @param {number} value */
export function signed(value) {
    const num = value || 0;
    return `${num >= 0 ? '+' : '-'}${num}`;
}

/** @param {string} [str] */
export function replaceSpacesWithUnderscore(str) {
    return str?.replace(/ /g, '_')?.toLowerCase() ?? '';
}

/**
 * @param {object} [obj]
 * @return {string}
 */
export function serialize(obj) {
    let result = '';
    for (const [name, value] of Object.entries(obj ?? {})) {
        result += name;
        if (value !== true && value !== 'true') {
            result += `=${value}`;
        }
        result += ';';
    }
    return result;
}

/**
 * @param {string} [str]
 * @return {object}
 */
export function deserialize(str) {
    const result = {};
    const entries = str?.split(';') ?? [];
    for (let i = 0; i < entries.length; i++) {
        const entry = entries[i];
        if (entry === '') {
            continue;
        }
        const [name, value] = entry.split('=');
        result[name] = value ?? true;
    }
    return result;
}
