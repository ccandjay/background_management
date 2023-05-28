import request from '@/utils/request'

export function echartsMembers (data) {
  return request({
    url: '/echarts/members',
    method: 'get'

  })
}
