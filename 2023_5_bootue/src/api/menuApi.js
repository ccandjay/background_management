import request from '@/utils/request'

export function menuSave (data) {
  return request({
    url: '/menu',
    method: 'post',
    data
  })
}

export function menuUpdate (data) {
  return request({
    url: '/menu',
    method: 'post',
    data: data
  })
}

export function menuDelete (data) {
  return request({
    url: '/menu/' + data.id,
    method: 'delete'
  })
}

export function menuPage (data) {
  return request({
    url: '/menu/page',
    method: 'get',
    params: data
  })
}

export function menuList (data) {
  return request({
    url: '/menu',
    method: 'get',
    params: data
  })
}

export function menuDeles (data) {
  return request({
    url: '/menu/del/batch',
    method: 'post',
    data: data.ids
  })
}

export function menuLogin (data) {
  return request({
    url: '/menu/login/',
    method: 'post',
    data
  })
}

export function menuIcons () {
  return request({
    url: '/menu/icons',
    method: 'get'
  })
}
